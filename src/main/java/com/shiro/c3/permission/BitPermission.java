package com.shiro.c3.permission;

import org.apache.shiro.authz.Permission;

import com.alibaba.druid.util.StringUtils;

/**
 * 按位分配权限
 * 
 * 规则 +资源字符串+权限位+实例ID
 *
 * 以+开头 中间通过+分割
 * 
 * 0 全部权限 0001 1 增 0010 2 改 0100 4 删 1000 8 查
 * 
 * 例 +user+10 表示对资源user拥有修改/查看（因为10转成二进制是1010）权限
 * 
 * @author zhanghaojie
 *
 */
public class BitPermission implements Permission {

	private String resourceIdentify;
	private int permissionBit;
	private String instanceId;

	public BitPermission(String permissionString) {
		String[] arrays = permissionString.split("\\+");
		if (arrays.length > 1) {
			resourceIdentify = arrays[1];
		}
		if (StringUtils.isEmpty(resourceIdentify)) {
			resourceIdentify = "*";
		}
		if (arrays.length > 2) {
			permissionBit = Integer.parseInt(arrays[2]);
		}
		if (arrays.length > 3) {
			instanceId = arrays[2];
		}
		if (StringUtils.isEmpty(instanceId)) {
			instanceId = "*";
		}
	}

	@Override
	public boolean implies(Permission p) {
		if (!(p instanceof BitPermission)) {
			return false;
		}
		BitPermission other = (BitPermission) p;

		if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
			return false;
		}

		if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
			return false;
		}

		if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "BitPermission [resourceIdentify=" + resourceIdentify + ", permissionBit=" + permissionBit
				+ ", instanceId=" + instanceId + "]";
	}

}
