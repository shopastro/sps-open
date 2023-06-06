package com.shopastro.sps.open.share.rpc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination implements Serializable {

    private Integer pageIndex = 1;

    private Integer pageSize = 20;

    private Long total;

}
