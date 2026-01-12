package com.oltpbenchmark.benchmarks.cfp;

import com.oltpbenchmark.WorkloadConfiguration;
import com.oltpbenchmark.api.BenchmarkModule;
import com.oltpbenchmark.api.Loader;
import com.oltpbenchmark.api.Worker;
import com.oltpbenchmark.benchmarks.cfp.procedures.UpdateCFP;

import java.util.ArrayList;
import java.util.List;

public class CFPBenchmark extends BenchmarkModule {

    public CFPBenchmark(WorkloadConfiguration workConf) {
        super(workConf);
    }

    @Override
    protected Package getProcedurePackageImpl() {
        return UpdateCFP.class.getPackage();
    }

    @Override
    protected Loader<CFPBenchmark> makeLoaderImpl() {
        return new Loader<CFPBenchmark>(this) {
            @Override
            public List<com.oltpbenchmark.api.LoaderThread> createLoaderThreads() {
                return new ArrayList<>();
            }
        };
    }

    @Override
    protected List<Worker<? extends BenchmarkModule>> makeWorkersImpl() {
        List<Worker<? extends BenchmarkModule>> workers = new ArrayList<>();
        int numTerminals = workConf.getTerminals();
        
        for (int i = 0; i < numTerminals; i++) {
            workers.add(new CFPWorker(this, i));
        }
        return workers;
    }
}