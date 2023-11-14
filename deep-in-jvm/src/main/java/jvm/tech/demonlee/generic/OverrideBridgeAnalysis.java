package jvm.tech.demonlee.generic;

import jvm.tech.demonlee.common.model.Customer;
import jvm.tech.demonlee.common.model.VIP;

/**
 * @author Demon.Lee
 * @date 2023-11-14 11:02
 * @desc generic override analysis for bridge method
 */
public class OverrideBridgeAnalysis {

    /*
     `javap -c` output:

      public static void main(java.lang.String[]);
    Code:
       0: new           #7                  // class jvm/tech/demonlee/common/model/VIP
       3: dup
       4: invokespecial #9                  // Method jvm/tech/demonlee/common/model/VIP."<init>":()V
       7: astore_1
       8: new           #10                 // class jvm/tech/demonlee/generic/VIPOnlyMerchant
      11: dup
      12: invokespecial #12                 // Method jvm/tech/demonlee/generic/VIPOnlyMerchant."<init>":()V
      15: astore_2
      16: aload_2
      17: aload_1
      18: invokevirtual #13                 // Method jvm/tech/demonlee/generic/Merchant.actionPrice:
      (Ljvm/tech/demonlee/common/model/Customer;)D
      21: dstore_3
      22: aload_2
      23: aload_1
      24: invokevirtual #19                 // Method jvm/tech/demonlee/generic/Merchant.actionPrice2:
      (Ljvm/tech/demonlee/common/model/Customer;)Ljava/lang/Number;
      27: astore        5
      29: return
     */
    public static void main(String[] args) {
        VIP vip = new VIP();
        Merchant<VIP> merchant = new VIPOnlyMerchant();
        double price = merchant.actionPrice(vip);
        Number price2 = merchant.actionPrice2(vip);
    }
}

class Merchant<T extends Customer> {

    public double actionPrice(T customer) {
        return 1.0d;
    }

    public Number actionPrice2(Customer customer) {
        return 1.0d;
    }
}

/*
`javap -c -v` output:

...
{
  jvm.tech.demonlee.generic.VIPOnlyMerchant();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method jvm/tech/demonlee/generic/Merchant."<init>":()V
         4: return
      LineNumberTable:
        line 40: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Ljvm/tech/demonlee/generic/VIPOnlyMerchant;

  public double actionPrice(jvm.tech.demonlee.common.model.VIP);
    descriptor: (Ljvm/tech/demonlee/common/model/VIP;)D
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: dconst_0
         1: dreturn
      LineNumberTable:
        line 45: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       2     0  this   Ljvm/tech/demonlee/generic/VIPOnlyMerchant;
            0       2     1 customer   Ljvm/tech/demonlee/common/model/VIP;

  public java.lang.Double actionPrice2(jvm.tech.demonlee.common.model.Customer);
    descriptor: (Ljvm/tech/demonlee/common/model/Customer;)Ljava/lang/Double;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: dconst_0
         1: invokestatic  #7                  // Method java/lang/Double.valueOf:(D)Ljava/lang/Double;
         4: areturn
      LineNumberTable:
        line 51: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Ljvm/tech/demonlee/generic/VIPOnlyMerchant;
            0       5     1 customer   Ljvm/tech/demonlee/common/model/Customer;

  public java.lang.Number actionPrice2(jvm.tech.demonlee.common.model.Customer);
    descriptor: (Ljvm/tech/demonlee/common/model/Customer;)Ljava/lang/Number;
    flags: (0x1041) ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: invokevirtual #13                 // Method actionPrice2:(Ljvm/tech/demonlee/common/model/Customer;)
         Ljava/lang/Double;
         5: areturn
      LineNumberTable:
        line 40: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Ljvm/tech/demonlee/generic/VIPOnlyMerchant;

  public double actionPrice(jvm.tech.demonlee.common.model.Customer);
    descriptor: (Ljvm/tech/demonlee/common/model/Customer;)D
    flags: (0x1041) ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: checkcast     #19                 // class jvm/tech/demonlee/common/model/VIP
         5: invokevirtual #21                 // Method actionPrice:(Ljvm/tech/demonlee/common/model/VIP;)D
         8: dreturn
      LineNumberTable:
        line 40: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  this   Ljvm/tech/demonlee/generic/VIPOnlyMerchant;
}
 */
class VIPOnlyMerchant extends Merchant<VIP> {

    // override input args type: `VIP`
    @Override
    public double actionPrice(VIP customer) {
        return 0.0d;
    }

    // override output args type: `Double`
    @Override
    public Double actionPrice2(Customer customer) {
        return 0.0d;
    }
}
