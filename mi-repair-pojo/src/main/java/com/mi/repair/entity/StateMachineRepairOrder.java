package com.mi.repair.entity;


import com.mi.repair.enums.RepairOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateMachineRepairOrder {

    private Long id;

    private RepairOrderStatus repairOrderStatus;

}
