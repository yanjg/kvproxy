package com.nfbank.tech.kvproxy.protocal.redis.netty4;

import java.io.IOException;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;

public class ErrorReply implements Reply<String> {
  public static final char MARKER = '-';
  public static final ErrorReply NYI_REPLY = new ErrorReply("Not yet implemented");
  private final String error;

  public ErrorReply(String error) {
    this.error = error;
  }

  @Override
  public String data() {
    return error;
  }

  @Override
  public void write(ByteBuf os) throws IOException {
    os.writeByte(MARKER);
    os.writeBytes(error.getBytes(Charsets.UTF_8));
    os.writeBytes(CRLF);
  }

  public String toString() {
    return error;
  }
}
