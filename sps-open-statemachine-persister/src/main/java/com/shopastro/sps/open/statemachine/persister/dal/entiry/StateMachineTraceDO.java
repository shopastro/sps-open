package com.shopastro.sps.open.statemachine.persister.dal.entiry;

import com.shopastro.sps.open.share.BaseDataObjectWithJsonColumn;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author ye.ly@shopastro-inc.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StateMachineTraceDO extends BaseDataObjectWithJsonColumn {

    Long tenantId;
    String bizId;
    String bizType;
    String statemachineId;
    Object beforeState;
    Object afterState;

    @Builder.Default
    Boolean error = false;
    Object exception;
    Object context;
}
