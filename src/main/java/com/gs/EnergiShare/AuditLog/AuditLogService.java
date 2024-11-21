package com.gs.EnergiShare.AuditLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public Page<AuditLog> listarLogs(String tableName, Pageable pageable) {
        if (tableName != null) {
            return auditLogRepository.findByTableNameContaining(tableName, pageable);
        }
        return auditLogRepository.findAll(pageable);
    }

    public Optional<AuditLog> getLogById(Integer id) {
        return auditLogRepository.findById(id);
    }

    public AuditLog createLog(AuditLog log) {
        return auditLogRepository.save(log);
    }

    public Optional<AuditLog> updateLog(Integer id, AuditLog logDetails) {
        return auditLogRepository.findById(id).map(log -> {
            log.setTableName(logDetails.getTableName());
            log.setActionType(logDetails.getActionType());
            log.setRecordId(logDetails.getRecordId());
            log.setUserName(logDetails.getUserName());
            return auditLogRepository.save(log);
        });
    }

    public boolean deleteLog(Integer id) {
        return auditLogRepository.findById(id).map(log -> {
            auditLogRepository.delete(log);
            return true;
        }).orElse(false);
    }
}
