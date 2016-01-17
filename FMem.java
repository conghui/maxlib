package maxlib;

import com.maxeler.maxcompiler.v2.kernelcompiler.KernelLib;
import com.maxeler.maxcompiler.v2.kernelcompiler.stdlib.memory.Memory;
import com.maxeler.maxcompiler.v2.kernelcompiler.types.base.DFEType;
import com.maxeler.maxcompiler.v2.kernelcompiler.types.base.DFEVar;

public abstract class FMem extends CKernelLib {
  protected Memory<DFEVar> fmem;
  protected int depth;
  protected DFEType elemType;
  protected DFEType addrType;

  public FMem(KernelLib owner) {
    super(owner);
    setDepth();
    setElemType();
    setAddrType();
    
    fmem = mem.alloc(elemType, depth);
  }

  public DFEVar read(DFEVar address) {
    return fmem.read(address.cast(addrType));
  }

  public void write(DFEVar address, DFEVar data, DFEVar enable) {
    fmem.write(address.cast(addrType), data.cast(elemType), enable);
  }

  public DFEType getElemType() {
    return elemType;
  }

  public DFEType getAddrType() {
    return addrType;
  }
  
  abstract protected void setDepth();
  abstract protected void setElemType();
  abstract protected void setAddrType();
}
