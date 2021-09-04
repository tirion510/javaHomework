package lock.exception;

import lock.errorcode.DistributedLockErrorCode;

public class DistributedLockException extends RuntimeException{


    private static final long serialVersionUID = 3171936442332439212L;

    /**
     * owl错误code
     */
    private DistributedLockErrorCode errorCode;

    /**
     * 提示信息
     */
    private String tipText;

    public DistributedLockErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(DistributedLockErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

    public DistributedLockException(DistributedLockErrorCode fdzterrorcode, String tipText){
        super(fdzterrorcode.getMsg());
        this.errorCode = fdzterrorcode;
        this.tipText = tipText;
    }

    public DistributedLockException(DistributedLockErrorCode errorCode){
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.tipText = errorCode.getMsg();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(errorCode.getCode())
                .append(" ")
                .append(errorCode.getMsg())
                .append(";")
                .append(tipText).toString();
    }




}
