package com.mi.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.*;
import com.mi.repair.enums.MaterialReqStatus;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.enums.StorageType;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.entity.MaterialReq;
import com.mi.repair.entity.OrderRepair;
import com.mi.repair.entity.Storage;
import com.mi.repair.exception.BaseException;
import com.mi.repair.mapper.MaterialReqMapper;
import com.mi.repair.mapper.StorageMapper;
import com.mi.repair.vo.OrderRepairSubmitVO;
import com.mi.repair.vo.OrderRepairVO;
import com.mi.repair.vo.RepairMaterialsVO;
import com.mi.repair.webSocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // 用户只能查询自己的工单
        Long id = BaseContext.getCurrentId();
        userOrderPageQueryDTO.setUserId(id);
        PageHelper.startPage(userOrderPageQueryDTO.getPage(), userOrderPageQueryDTO.getPageSize());
        Page<OrderRepair> page = orderRepairMapper.pageQueryByUserId(userOrderPageQueryDTO);
        long total = page.getTotal();
        List<OrderRepair> result = page.getResult();
        List<OrderRepairVO> pageInfo = new ArrayList<>(result.size());
        for(OrderRepair order : result){
            OrderRepairVO orderVO = new OrderRepairVO();
            BeanUtils.copyProperties(order, orderVO);
            for(RepairOrderStatus status : RepairOrderStatus.values()){
                if(order.getStatus().equals(status.getCode())){
                    orderVO.setStatusInfo(status.getDescription());
                }
            }
            pageInfo.add(orderVO);
        }
        return new PageResult(total, pageInfo);
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
        Long id = BaseContext.getCurrentId();
        workerOrderPageQueryDTO.setWorkerId(id);
        Page<OrderRepair> page = orderRepairMapper.pageQueryByWorker(workerOrderPageQueryDTO);
        long total = page.getTotal();
        List<OrderRepair> result = page.getResult();
        List<OrderRepairVO> pageInfo = new ArrayList<>(result.size());
        for(OrderRepair order : result){
            OrderRepairVO orderVO = new OrderRepairVO();
            BeanUtils.copyProperties(order, orderVO);
            for(RepairOrderStatus status : RepairOrderStatus.values()){
                if(order.getStatus().equals(status.getCode())){
                    orderVO.setStatusInfo(status.getDescription());
                }
            }
            pageInfo.add(orderVO);
        }
        return new PageResult(total, pageInfo);
    }

    /**
     * 同时申请多种材料
     * @param list  申请的材料列表
     * @return
     */
    @Override
    public List<RepairMaterialsVO> applyMaterials(List<RepairMaterialsDTO> list) {
        List<RepairMaterialsVO> voList = new ArrayList<>();
        for(RepairMaterialsDTO repairMaterialsDTO : list){
            RepairMaterialsVO vo = applyOne(repairMaterialsDTO);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 只申请一种材料
     * @param repairMaterialsDTO
     * @return
     */
    public RepairMaterialsVO applyOne(RepairMaterialsDTO repairMaterialsDTO) {
        MaterialReq materialReq = new MaterialReq();
        // 用于删减库存
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setId(repairMaterialsDTO.getId());
        storageDTO.setAmount(repairMaterialsDTO.getMaterialAmount());
        BeanUtils.copyProperties(repairMaterialsDTO, materialReq);
        // 剩余字段填充
        // 获取当前工程师id
        Long workerId = BaseContext.getCurrentId();
        materialReq.setWorkerId(workerId);
        Storage material = storageMapper.getStorage(storageDTO.getId());
        if(material.getAmount() < repairMaterialsDTO.getMaterialAmount()){
            materialReq.setStatus(MaterialReqStatus.OUT_OF_STOCK.getCode());
            throw new BaseException("材料不足");
        }{
            // 删减库存
            materialReq.setStatus(MaterialReqStatus.SUCCESS.getCode());
            storageMapper.subStorage(storageDTO);
        }
        // 前端会传来id，但是这是对应storage的id
        materialReq.setId(null);
        materialReq.setMaterialId(material.getId());
        LocalDateTime time = LocalDateTime.now();
        materialReq.setCreateTime(time);
        materialReq.setUpdateTime(time);

        // 插入数据库
        long i = matrialReqMapper.submit(materialReq);
        RepairMaterialsVO materialsVO = new RepairMaterialsVO(i, time);
        return materialsVO;
    }
    public RepairMaterialsVO applyOne1(RepairMaterialsDTO repairMaterialsDTO) {
        MaterialReq materialReq = new MaterialReq();
        Long storageId = repairMaterialsDTO.getId();
        Storage storage = new Storage();
        BeanUtils.copyProperties(repairMaterialsDTO, materialReq);
        storage.setName(repairMaterialsDTO.getMaterialName());
        for(StorageType storageType : StorageType.values()){
            if(storageType.getName().equals(repairMaterialsDTO.getMaterialTypeName())){
                storage.setType(storageType.getCode());
            }
        }
        // 剩余字段填充
        // 获取当前工程师id
        Long workerId = BaseContext.getCurrentId();
        materialReq.setWorkerId(workerId);
        Storage material = storageMapper.getStorage(storageId);
        //Storage material = storageMapper.getStorageByName(repairMaterialsDTO.getMaterialName());
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
