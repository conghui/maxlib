package maxlib;

import com.maxeler.maxcompiler.v2.kernelcompiler.KernelLib;
import com.maxeler.maxcompiler.v2.kernelcompiler.types.base.DFEVar;

/**
 * 
 * KernelLib with additional utility functions
 * 
 * @author Conghui He
 *
 */
public class CKernelLib extends KernelLib {

  public CKernelLib(KernelLib owner) {
    super(owner);
  }

  /**
   * Get the method name for a depth in call stack.
   * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
   * @return method name
   */
  protected static String getMethodName(final int depth)
  {
    final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

//    return ste[ste.length - 1 - depth].getMethodName(); //Thank you Tom Tresansky
    return ste[2 + depth].getMethodName(); /// works for JRE 6
  }
  
  /**
   * similar to debug.simPrintf
   * @param condition
   * @param format
   * @param args
   */
  protected void printf(DFEVar condition, String format, Object ...args) {
    String className = this.getClass().getSimpleName();
    String parentMethodName = getMethodName(1);
    String prefix = "[" + className + "." + parentMethodName + "]: ";
    debug.simPrintf(condition, prefix + format, args);
  }
  
  protected void printf(String format, Object ... args) {
    debug.simPrintf(constant.var(true), format, args);
  }
}
