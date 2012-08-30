package org.pitest.mutationtest.incremental;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class NullWriterFactory implements WriterFactory {

  private final PrintWriter pw;

  public NullWriterFactory() {
    this.pw = new PrintWriter(nullOutputStream());
  }

  private OutputStream nullOutputStream() {
    return new OutputStream() {
      @Override
      public void write(final int b) throws IOException {

      }
    };
  }

  public PrintWriter create() {
    return this.pw;
  }

  public void close() {
    this.pw.close();
  }

}