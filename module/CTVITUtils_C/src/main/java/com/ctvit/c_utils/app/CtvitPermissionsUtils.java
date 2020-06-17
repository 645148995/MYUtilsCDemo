package com.ctvit.c_utils.app;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.ctvit.c_utils.CtvitUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 基于EasyPermissions
 */
public class CtvitPermissionsUtils {

    /**
     * @return 未成功申请的权限 或 全部申请成功的权限
     */
    public static String[] permissions(@NonNull @Size(min = 1) String... permissions) {
        List<String> deniedList = new ArrayList<>();
        //权限没有都申请成功
        if (!EasyPermissions.hasPermissions(CtvitUtils.getContext(), permissions)) {
            //遍历找到没有申请成功的权限
            for (String p : permissions) {
                if (!EasyPermissions.hasPermissions(CtvitUtils.getContext(), p))
                    deniedList.add(p);
            }
        }

        String[] tempDeniedPermissions;
        if (deniedList.isEmpty())
            tempDeniedPermissions = permissions;
        else
            tempDeniedPermissions = deniedList.toArray(new String[deniedList.size()]);
        return tempDeniedPermissions;
    }

}
