package com.example.inventoryModule.common.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-Tenant-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tenantId = request.getHeader(TENANT_HEADER);
        String roleHeader = request.getHeader("X-User-Role"); 
        
        if (roleHeader == null || roleHeader.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("""
                    {"message":"Missing required header: X-User-Role"}
                """);
                return;
            }
       // RoleContext.setRole(roleHeader);
        
        if ("GLOBAL_ADMIN".equalsIgnoreCase(roleHeader)) {
                if (tenantId != null && !tenantId.isBlank()) {
                    TenantContext.setTenantId(tenantId);
                } 
            }
        else{
            if(tenantId == null || tenantId.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("""
                    {"message":"Missing required header: X-Tenant-Id"}
                """);
                return;
            }
            TenantContext.setTenantId(tenantId);
        }

        try {
            //TenantContext.setTenantId(tenantId);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
