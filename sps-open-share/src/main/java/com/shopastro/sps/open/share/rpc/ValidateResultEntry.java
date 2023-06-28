package com.shopastro.sps.open.share.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResultEntry implements Serializable {
    String path;
    String message;

    Object invalidValue;

}