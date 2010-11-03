package ICS.SND.Utilities;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;

public class Processor implements IProcessor {

  @Override
  public void Process(IEntry entry) {
    System.out.print("title: " + entry);
  }
}
