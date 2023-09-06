package com.ecom.shopping_cart.entities;

public enum Roles{
        ROLE_USER(1),
        ROLE_ADMIN(2),
        ROLE_MANAGER(3);
        private final int roleId;

        Roles(int roleId) {
            this.roleId = roleId;
        }
        public int getRoleId() {
            return roleId;
        }

    }

