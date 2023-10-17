package com.zhugalcf.superkassa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("sk_example_table")
public class SkExample {

    @Id
    private long id;
    private PGobject obj;
    @Column("sk_example_table_id")
    private SkExampleRef skExampleRef;
    @Version
    private long version;
}
