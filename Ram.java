package maxlib;

import com.maxeler.maxcompiler.v2.kernelcompiler.KernelLib;
import com.maxeler.maxcompiler.v2.kernelcompiler.stdlib.core.Count.WrapMode;
import com.maxeler.maxcompiler.v2.kernelcompiler.types.base.DFEVar;

public abstract class Ram extends FMem {
  protected DFEVar stopAtMaxCounter;
  protected DFEVar initData;
  
  public Ram(KernelLib owner, double initData) {
    super(owner);

    this.initData = constant.var(initData).cast(elemType);
    
    stopAtMaxCounter = control.count.makeCounter(
                    control.count.makeParams(32)
                    .withWrapMode(WrapMode.STOP_AT_MAX)
                    ) .getCount();
        
  }

  @Override
  public void write(DFEVar address, DFEVar data, DFEVar enable) {
    /**
     * for initialization
     */
    DFEVar initAddr = stopAtMaxCounter.cast(addrType);
    DFEVar initEnable = stopAtMaxCounter < this.depth;
    
    DFEVar finalAddr = initEnable ? initAddr : address.cast(addrType);
    DFEVar finalData = initEnable ? initData : data;
    DFEVar finalEnable = initEnable ? initEnable : enable;
    super.write(finalAddr, finalData, finalEnable);
  }

}
