package com.nfbank.tech.kvproxy.redis;

import com.nfbank.tech.kvproxy.protocal.redis.netty4.Reply;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Write a reply.
 */
public class RedisReplyEncoder extends MessageToByteEncoder<Reply> {
  @Override
  public void encode(ChannelHandlerContext ctx, Reply msg, ByteBuf out) throws Exception {
    msg.write(out);
  }
}
