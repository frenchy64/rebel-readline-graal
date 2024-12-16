package rebel_readline.jline_api;

public class RebelLineReaderImpl extends org.jline.reader.impl.LineReaderImpl {
  public RebelLineReaderImpl(org.jline.terminal.Terminal terminal) throws java.io.IOException { super(terminal); }
  public RebelLineReaderImpl(org.jline.terminal.Terminal terminal, String appName) throws java.io.IOException { super(terminal, appName); }
  public RebelLineReaderImpl(org.jline.terminal.Terminal terminal, String appName, java.util.Map<String, Object> variables) { super(terminal, appName, variables); }
  public org.jline.terminal.Size getSize() { return this.size; }
  public boolean getReading() { return this.reading; }
  public void setPost(java.util.function.Supplier<org.jline.utils.AttributedString> s) { this.post = s; }
  public boolean pubSelfInsert() { return super.selfInsert(); }
}
