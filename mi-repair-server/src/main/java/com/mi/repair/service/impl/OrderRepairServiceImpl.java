package com.mi.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.*;
import com.mi.repair.entity.OrderPay;
import com.mi.repair.enums.*;
import com.mi.repair.mapper.OrderPayMapper;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.entity.MaterialReq;
import com.mi.repair.entity.OrderRepair;
import com.mi.repair.entity.Storage;
import com.mi.repair.exception.BaseException;
import com.mi.repair.mapper.MaterialReqMapper;
import com.mi.repair.mapper.StorageMapper;
import com.mi.repair.service.ScheduleService;
import com.mi.repair.utils.StateMachineUtil;
import com.mi.repair.vo.OrderRepairSubmitVO;
import com.mi.repair.vo.OrderRepairVO;
import com.mi.repair.vo.RepairMaterialsVO;
import com.mi.repair.webSocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private MaterialReqMapper materialReqMapper;
    @Autowired
    private OrderPayMapper orderPayMapper;
//    @Autowired
//    private WebSocketServer webSocketServer;
    @Autowired
    private StateMachineUtil stateMachineUtil;

    @Autowired
    private ScheduleService scheduleService;

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
        // 5、插入进度
        scheduleService.insertSchedule(orderRepair.getId(),RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE.getCode(),0);
//        // 6、下单成功，向工程师端发起来单提醒
//        Map map = new HashMap();
//        map.put("type", 1);
//        map.put("orderId", orderRepair.getId());
//        map.put("content", "订单号:" + orderRepair.getId());
//        webSocketServer.sendToAllClient(JSON.toJSONString(map));

        return submitVO;
    }

    @Override
    public int confirm(Long orderId){
        // 1、 查找维修单信息
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        // 2、插入进度
        scheduleService.insertSchedule(orderId,RepairOrderStatus.CONFIRMED.getCode(),0);
        // 3、 获取当前用户 id
        Long userId = BaseContext.getCurrentId();
        // 4、 判断当前维修单是否属于该用户
        if(!userId.equals(orderRepair.getUserId())){
            return 0;
        }
        return orderRepairMapper.confirm(orderId);
    }

    @Override
    public PageResult pageQuery(UserOrderPageQueryDTO userOrderPageQueryDTO){
        Long userId = BaseContext.getCurrentId();
        userOrderPageQueryDTO.setUserId(userId);
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
        // 插入进度
        scheduleService.insertSchedule(orderId,RepairOrderStatus.CANCEL.getCode(),0);
        return orderRepairMapper.delete(orderId);
    }

    @Override
    public int workerConfirm(Long orderId){
        int code = RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION.getCode();
        // 插入进度
        scheduleService.insertSchedule(orderId,code,1);
        Long workerId = BaseContext.getCurrentId();
        return orderRepairMapper.updateStatus(orderId, code,workerId);
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
        if (list != null) {
            Long orderId = list.get(0).getOrderId();
            for (RepairMaterialsDTO repairMaterialsDTO : list) {
                RepairMaterialsVO vo = applyOne(repairMaterialsDTO);
                voList.add(vo);
            }
            if (orderId == null) {
                throw new BaseException("申请材料订单号为空");
            }
            stateMachineUtil.saveAndSendEvent(orderId, RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS);
            orderRepairMapper.updateStatusById(orderId,RepairOrderStatus.REPAIR.getCode());
            scheduleService.insertSchedule(orderId,RepairOrderStatus.REPAIR.getCode(),1);
        }
        return voList;
    }

    @Override
    public int orderRepairSuccess(Long orderId) {
        // 1、 查找维修单信息
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        // 2、 获取当前工程师 id
        Long workerId = BaseContext.getCurrentId();
        // 3、 判断当前维修单是否属于该工程师以及维修单当前状态是否为维修状态
        if(workerId.equals(orderRepair.getWorkerId()) && orderRepair.getStatus().equals(RepairOrderStatus.REPAIR.getCode())){
            scheduleService.insertSchedule(orderId,RepairOrderStatus.RETEST.getCode(),1);
            return orderRepairMapper.updateStatusById(orderId,RepairOrderStatus.RETEST.getCode());
        }
        // 4、 向用户发起短信通知

        return 0;
    }

    @Override
    public int orderRepairFailed(Long orderId) {
        // 1、 查找维修单信息
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        // 2、 获取当前工程师 id
        Long workerId = BaseContext.getCurrentId();
        // 3、 判断当前维修单是否属于该工程师以及维修单当前状态是否为维修状态
        if(workerId.equals(orderRepair.getWorkerId()) && orderRepair.getStatus().equals(RepairOrderStatus.REPAIR.getCode())){
            scheduleService.insertSchedule(orderId,RepairOrderStatus.APPLICATION_MATERIALS.getCode(),1);
            return orderRepairMapper.updateStatusById(orderId,RepairOrderStatus.APPLICATION_MATERIALS.getCode());
        }
        return 0;
    }

    /**
     * 归还设备
     * @param id
     * @return
     */
    @Override
    public int returnDevice(Long id) {
        OrderRepair orderRepair = orderRepairMapper.selectById(id);
        // 2、 获取当前工程师 id
        Long workerId = BaseContext.getCurrentId();
        // 3、 判断当前维修单是否属于该工程师以及维修单当前状态是否为维修状态
        if(workerId.equals(orderRepair.getWorkerId()) && orderRepair.getStatus().equals(RepairOrderStatus.PAYED.getCode())){
            scheduleService.insertSchedule(id,RepairOrderStatus.DONE.getCode(),1);
            return orderRepairMapper.updateStatusById(id,RepairOrderStatus.DONE.getCode());
        }
        return 0;
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
        materialReq.setStatus(0);
        // 材料价格
        BigDecimal priceSum = material.getPrice().multiply(BigDecimal.valueOf(materialReq.getMaterialAmount()));
        materialReq.setPriceSum(priceSum);
        LocalDateTime time = LocalDateTime.now();
        materialReq.setCreateTime(time);
        materialReq.setUpdateTime(time);

        // 插入数据库
        long i = materialReqMapper.submit(materialReq);
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
        long i = materialReqMapper.submit(materialReq);
        RepairMaterialsVO materialsVO = new RepairMaterialsVO(i, time);
        return materialsVO;
    }

    /**
     * 创建支付订单
     * @param orderId
     * @return
     */
    @Override
    public OrderPayDTO createPayOrder(Long orderId) {
        List<MaterialReq> materialReqs = materialReqMapper.selectByOrderId(orderId);
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        orderPayDTO.setOrderId(orderId);
        orderPayDTO.setUserId(orderRepair.getUserId());
        orderPayDTO.setWorkerId(orderRepair.getWorkerId());
        BigDecimal price = new BigDecimal(0);
        for(MaterialReq materialReq : materialReqs){
            price = price.add(materialReq.getPriceSum());
        }
        orderPayDTO.setPrice(price);
        orderPayDTO.setStatus(OrderPayStatus.NO.getCode());
        LocalDateTime time = LocalDateTime.now();
        OrderPay orderPay = new OrderPay();
        BeanUtils.copyProperties(orderPayDTO, orderPay);
        orderPay.setCreateTime(time);
        orderPay.setUpdateTime(time);
        OrderPay pay = orderPayMapper.selectPayOrderById(orderId);
        if(pay == null){
            orderPayMapper.insert(orderPay);
        }
        return orderPayDTO;
    }
}
