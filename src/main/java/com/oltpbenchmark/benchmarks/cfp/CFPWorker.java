package com.oltpbenchmark.benchmarks.cfp;

import com.oltpbenchmark.api.Procedure.UserAbortException;
import com.oltpbenchmark.api.TransactionType;
import com.oltpbenchmark.api.Worker;
import com.oltpbenchmark.types.TransactionStatus;
import com.oltpbenchmark.benchmarks.cfp.procedures.UpdateCFP;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 

public class CFPWorker extends Worker<CFPBenchmark> {

    private final List<String> partIds = new ArrayList<>();
    
    private final Random rand = new Random();

    public CFPWorker(CFPBenchmark benchmarkModule, int id) {
        super(benchmarkModule, id);
        
        // CSVファイルの読み込み
        String csvFile = "/root/framework_APP/setting/dataset/0/30000/0/cfpvalA.csv"; 
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] columns = line.split(",");

                if (columns.length > 0) {
                    String targetId = columns[0].trim();

                    if (isFirstLine) {
                        isFirstLine = false;
                        if (targetId.equalsIgnoreCase("partid")) {
                            continue;
                        }
                    }
                    partIds.add(targetId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV load failed: " + csvFile, e);
        }

        if (partIds.isEmpty()) {
            throw new RuntimeException("CSV file is empty or only contains header!");
        }
    }

    @Override
    protected TransactionStatus executeWork(Connection conn, TransactionType nextTransaction) throws UserAbortException, SQLException {
        try {
            Class<?> procClass = nextTransaction.getProcedureClass();

            if (procClass.equals(UpdateCFP.class)) {
                UpdateCFP proc = this.getProcedure(UpdateCFP.class);
                
                int index = this.rand.nextInt(partIds.size());
                String targetId = partIds.get(index);
                
                proc.run(conn, targetId);
            }

            conn.commit();
            return TransactionStatus.SUCCESS;
        } catch (SQLException e) {
            conn.rollback();
            return TransactionStatus.RETRY;
        }
    }
}