package com.mi_repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mi_repair.context.BaseContext;
import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.dto.RepairMaterialsDTO;
import com.mi_repair.dto.UserOrderPageQueryDTO;
import com.mi_repair.dto.WorkerOrderPageQueryDTO;
import com.mi_repair.entity.MaterialReq;
import com.mi_repair.entity.OrderRepair;
import com.mi_repair.entity.Storage;
import com.mi_repair.enums.RepairOrderStatus;
import com.mi_repair.exception.BaseException;
import com.mi_repair.mapper.MaterialReqMapper;
import com.mi_repair.mapper.OrderRepairMapper;
import com.mi_repair.mapper.StorageMapper;
import com.mi_repair.result.PageResult;
import com.mi_repair.service.OrderRepairService;
import com.mi_repair.vo.OrderRepairSubmitVO;
import com.mi_repair.vo.RepairMaterialsVO;
import com.mi_repair.webSocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:08
 */
@Service
public class OrderRepairServiceImpl implements OrderRepairService {
    @Autowired
    private OrderRepairMapper orderRepairMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private MaterialReqMapper matrialReqMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public OrderRepairSubmitVO submitOrderRepair(OrderRepairSubmitDTO orderRepairSubmitDTO) {
        Long id = BaseContext.getCurrentId();
        // 2、对象转换
        OrderRepair orderRepair = new OrderRepair();
        BeanUtils.copyProperties(orderRepairSubmitDTO, orderRepair);
        // 3、剩余字段信息填充
        orderRepair.setUserId(id);
        orderRepair.setStatus(0);
        LocalDateTime time = LocalDateTime.now();
        orderRepair.setCreateTime(time);
        orderRepair.setUpdateTime(time);
        // 4、插入数据
        orderRepairMapper.submit(orderRepair);
        OrderRepairSubmitVO submitVO = OrderRepairSubmitVO.builder().id(orderRepair.getId()).orderTime(time).build();
        // 5、下单成功，向工程师端发起来单提醒   TODO: 待前后端联调
        Map map = new HashMap();
        map.put("type", 1);
        map.put("orderId", orderRepair.getId());
        map.put("content", "订单号:" + orderRepair.getId());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));

        return submitVO;
    }

    @Override
    public int confirm(Long orderId){
        // 1、 查找维修单信息
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        // 2、 获取当前用户 id
        Long userId = BaseContext.getCurrentId();
        // 3、 判断当前维修单是否属于该用户
        if(!userId.equals(orderRepair.getUserId())){
            return 0;
        }
        return orderRepairMapper.confirm(orderId);
    }

    @Override
    public PageResult pageQuery(UserOrderPageQueryDTO userOrderPageQueryDTO){

        PageHelper.startPage(userOrderPageQueryDTO.getPage(), userOrderPageQueryDTO.getPageSize());
        Page<OrderRepair> page = orderRepairMapper.pageQueryByUserId(userOrderPageQueryDTO);

        long total = page.getTotal();
        List<OrderRepair> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public int delete(Long orderId) {
        // 1、 查找维修单信息
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        // 2、 获取当前用户 id
        Long userId = BaseContext.getCurrentId();
        System.out.println(RepairOrderStatus.REPAIR.getCode());
        // 3、 判断当前维修单是否属于该用户以及维修单当前状态能否取消
        if(!userId.equals(orderRepair.getUserId()) || orderRepair.getStatus().equals(RepairOrderStatus.REPAIR.getCode())){
            return 0;
        }
        return orderRepairMapper.delete(orderId);
    }

    @Override
    public int workerConfirm(Long orderId){
        int code = RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION.getCode();
        return orderRepairMapper.updateStatus(orderId, code);
    }

    @Override
    public PageResult pageQuery(WorkerOrderPageQueryDTO workerOrderPageQueryDTO) {
        PageHelper.startPage(workerOrderPageQueryDTO.getPage(), workerOrderPageQueryDTO.getPageSize());
        Page<OrderRepair> page = orderRepairMapper.pageQueryByWorker(workerOrderPageQueryDTO);
        long total = page.getTotal();
        List<OrderRepair> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public RepairMaterialsVO applyMaterials(RepairMaterialsDTO repairMaterialsDTO) {
        MaterialReq materialReq = new MaterialReq();
        BeanUtils.copyProperties(repairMaterialsDTO, materialReq);
        // 剩余字段填充
        // 获取当前工程师id
        Long workerId = BaseContext.getCurrentId();
        materialReq.setWorkerId(workerId);
        Storage material = storageMapper.getStorageByName(repairMaterialsDTO.getMaterialName());
        if(material.getAmount() < repairMaterialsDTO.getMaterialAmount()){
            throw new BaseException("材料不足");
        }
        materialReq.setMaterialId(material.getId());
        materialReq.setStatus(0);
        LocalDateTime time = LocalDateTime.now();
        materialReq.setCreateTime(time);
        materialReq.setUpdateTime(time);

        // 插入数据库
        long i = matrialReqMapper.submit(materialReq);
        RepairMaterialsVO materialsVO = new RepairMaterialsVO(i, time);
        return materialsVO;
    }
}
