package com.summary.common.core.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息基类
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMqMessage implements Serializable {

    /**
     * 消息id
     */
    private String messageId;
}
