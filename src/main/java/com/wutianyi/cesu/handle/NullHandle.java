package com.wutianyi.cesu.handle;

public class NullHandle implements Handle
{
    String[] citys = new String[]
    { "汕头市", "广州市", "梅州市", "汕尾市" };

    @Override
    public String handle(String str)
    {
        String province = "广东省";
        int random = (int) (Math.random() * 4);
        return province + citys[random];
    }

}
