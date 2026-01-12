DROP TABLE IF EXISTS A_assembler;
DROP TABLE IF EXISTS B_assembler;
DROP TABLE IF EXISTS C_assembler;

DROP TABLE IF EXISTS A_parts_tree;
DROP TABLE IF EXISTS B_parts_tree;
DROP TABLE IF EXISTS C_parts_tree;

DROP TABLE IF EXISTS A_cfpval;
DROP TABLE IF EXISTS B_cfpval;
DROP TABLE IF EXISTS C_cfpval;

CREATE TABLE IF NOT EXISTS A_cfpval(
        partid VARCHAR(50) PRIMARY KEY,
        cfp DECIMAL(18,4) NOT NULL,
        co2 DECIMAL(18,4) NOT NULL
    )
CREATE INDEX index_cfpval ON A_cfpval (partid)

CREATE TABLE IF NOT EXISTS A_parts_tree (
    partid VARCHAR(50),
    parents_partid VARCHAR(50),
    qty BIGINT,
    UNIQUE (partid, parents_partid)
)

CREATE INDEX index_parts_tree ON A_parts_tree (partid)

CREATE TABLE IF NOT EXISTS A_assembler (
    partid VARCHAR(50) PRIMARY KEY,
    assembler VARCHAR(50) NOT NULL
)

CREATE INDEX index_assembler ON A_assembler (partid)


CREATE TABLE IF NOT EXISTS B_cfpval (
    partid VARCHAR(50) PRIMARY KEY,
    cfp DECIMAL(18, 4) NOT NULL ,
    co2 DECIMAL(18, 4) NOT NULL
) ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuB:3306/offchaindb/B_cfpval';


CREATE TABLE IF NOT EXISTS B_parts_tree (
    partid VARCHAR(50),
    parents_partid VARCHAR(50),
    qty bigint,
    UNIQUE (partid, parents_partid)
)ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuB:3306/offchaindb/B_parts_tree';


CREATE TABLE IF NOT EXISTS B_assembler (
    partid VARCHAR(50) PRIMARY KEY,
    assembler VARCHAR(50) NOT NULL
)ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuB:3306/offchaindb/B_assembler'; 


CREATE TABLE IF NOT EXISTS C_cfpval (
    partid VARCHAR(50) PRIMARY KEY,
    cfp DECIMAL(18, 4) NOT NULL ,
    co2 DECIMAL(18, 4) NOT NULL
) ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuC:3306/offchaindb/C_cfpval';


CREATE TABLE IF NOT EXISTS C_parts_tree (
    partid VARCHAR(50),
    parents_partid VARCHAR(50),
    qty bigint,
    UNIQUE (partid, parents_partid)
)ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuC:3306/offchaindb/C_parts_tree';


CREATE TABLE IF NOT EXISTS C_assembler (
    partid VARCHAR(50) PRIMARY KEY,
    assembler VARCHAR(50) NOT NULL
)ENGINE=FEDERATED
CONNECTION='mysql://deploy_user:password@ubuntuC:3306/offchaindb/C_assembler'; 