package com.shopastro.sps.open.share;

import com.shopastro.sps.open.share.rpc.RpcResult;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by liye on 2023/1/3.
 */
@Slf4j
public class TypeConvertor {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <T, R> R convert(T src, Class<R> targetClz) {
        if (src == null) {
            return null;
        }
        return mapper.map(src, targetClz);
    }

    public static <T, R> List<R> convertList(List<T> rows, Class<R> targetClz) {
        if (rows == null) {
            return null;
        }
        return rows.stream()
                .map(it -> convert(it, targetClz))
                .collect(Collectors.toList());
    }

    public static <T, R> RpcResult<R> convertSuccess(T src, Class<R> targetClz) {
        return RpcResult.success(convert(src, targetClz));
    }

    public static <T, R> RpcResult<R> convertSuccess(Supplier<T> src, Class<R> targetClz) {
        return convertSuccess(src.get(), targetClz);
    }


}
