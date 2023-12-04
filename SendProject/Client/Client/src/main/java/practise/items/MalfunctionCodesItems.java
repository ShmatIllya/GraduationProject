package practise.items;

public class MalfunctionCodesItems {
    int code;
    String fullCode, system, cause;
    public MalfunctionCodesItems( int code, String fullCode, String system, String cause)
    {
        this.code = code;
        this.fullCode = fullCode;
        this.system = system;
        this.cause = cause;
    }

    public int getCode() {
        return code;
    }

    public String getFullCode() {
        return fullCode;
    }
    public String getSystem()
    {
        return system;
    }

    public String getCause() {
        return cause;
    }
}
