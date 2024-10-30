package iticbcn.xifratge;

public class TextXifrat {
    public byte[] bytes;

    public TextXifrat(byte[] bytes){
        this.bytes = bytes;
    }

    @Override
    public String toString(){
        return bytes.toString();
    }

    public byte[] getBytes(){
        return bytes;
    }

}
