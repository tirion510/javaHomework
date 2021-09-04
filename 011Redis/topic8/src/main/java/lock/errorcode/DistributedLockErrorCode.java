package lock.errorcode;

import com.cy.sz.entity.result.ResultCode;

public class DistributedLockErrorCode extends ResultCode {

    public final static DistributedLockErrorCode MANAGER_NOT_FOUND = new DistributedLockErrorCode(-21,
        "distributed manager not found");

    public final static DistributedLockErrorCode TIME_NOT_VALID = new DistributedLockErrorCode(-22,
        "distributed time not valid");

    public final static DistributedLockErrorCode LOCK_FAILED = new DistributedLockErrorCode(-23,
        "distributed lock duplicate");

    public final static DistributedLockErrorCode LOCK_KEY_DUPLICATE_AND_WAIT_TIMEOUT = new DistributedLockErrorCode(-24,
        "distributed lock duplicate, and wait timeout");

    public final static DistributedLockErrorCode LOCK_KEY_DUPLICATE_AND_DENY_WAIT = new DistributedLockErrorCode(-25,
        "distribute lock key duplicate, and deny wait, abort");

    public DistributedLockErrorCode(int code, String msg) {
        super(code, msg);
    }
}
