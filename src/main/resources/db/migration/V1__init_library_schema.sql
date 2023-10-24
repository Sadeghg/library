create table library.BATCH_JOB_EXECUTION_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table library.BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID bigint       not null
        primary key,
    VERSION         bigint       null,
    JOB_NAME        varchar(100) not null,
    JOB_KEY         varchar(32)  not null,
    constraint JOB_INST_UN
        unique (JOB_NAME, JOB_KEY)
);

create table library.BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID bigint        not null
        primary key,
    VERSION          bigint        null,
    JOB_INSTANCE_ID  bigint        not null,
    CREATE_TIME      datetime(6)   not null,
    START_TIME       datetime(6)   null,
    END_TIME         datetime(6)   null,
    STATUS           varchar(10)   null,
    EXIT_CODE        varchar(2500) null,
    EXIT_MESSAGE     varchar(2500) null,
    LAST_UPDATED     datetime(6)   null,
    constraint JOB_INST_EXEC_FK
        foreign key (JOB_INSTANCE_ID) references library.BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
);

create table library.BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint JOB_EXEC_CTX_FK
        foreign key (JOB_EXECUTION_ID) references library.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

create table library.BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID bigint        not null,
    PARAMETER_NAME   varchar(100)  not null,
    PARAMETER_TYPE   varchar(100)  not null,
    PARAMETER_VALUE  varchar(2500) null,
    IDENTIFYING      char          not null,
    constraint JOB_EXEC_PARAMS_FK
        foreign key (JOB_EXECUTION_ID) references library.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

create table library.BATCH_JOB_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table library.BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  bigint        not null
        primary key,
    VERSION            bigint        not null,
    STEP_NAME          varchar(100)  not null,
    JOB_EXECUTION_ID   bigint        not null,
    CREATE_TIME        datetime(6)   not null,
    START_TIME         datetime(6)   null,
    END_TIME           datetime(6)   null,
    STATUS             varchar(10)   null,
    COMMIT_COUNT       bigint        null,
    READ_COUNT         bigint        null,
    FILTER_COUNT       bigint        null,
    WRITE_COUNT        bigint        null,
    READ_SKIP_COUNT    bigint        null,
    WRITE_SKIP_COUNT   bigint        null,
    PROCESS_SKIP_COUNT bigint        null,
    ROLLBACK_COUNT     bigint        null,
    EXIT_CODE          varchar(2500) null,
    EXIT_MESSAGE       varchar(2500) null,
    LAST_UPDATED       datetime(6)   null,
    constraint JOB_EXEC_STEP_FK
        foreign key (JOB_EXECUTION_ID) references library.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

create table library.BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint STEP_EXEC_CTX_FK
        foreign key (STEP_EXECUTION_ID) references library.BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
);

create table library.BATCH_STEP_EXECUTION_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
        unique (UNIQUE_KEY)
);

create table library.book
(
    id              bigint auto_increment
        primary key,
    available_count int          not null,
    isbn            varchar(255) null,
    publish_date    date         null,
    publish_set     int          null,
    published_year  int          not null,
    title           varchar(255) null,
    constraint book_isbn_unique_key
        unique (isbn),
    check (`available_count` >= 0)
);

create table library.customer
(
    id              bigint auto_increment
        primary key,
    birth_date      date                            null,
    checkout_count  int                             not null,
    customer_score  int                             not null,
    father_name     varchar(255)                    null,
    gender          enum ('FEMALE', 'MALE', 'NONE') null,
    last_name       varchar(255)                    null,
    membership_date date                            null,
    name            varchar(255)                    null,
    national_code   varchar(10)                     null,
    constraint customer_national_unique_key
        unique (national_code),
    check ((`checkout_count` >= 0) and (`checkout_count` <= 2))
);

create table library.checkout
(
    id              bigint auto_increment
        primary key,
    checkout_number varchar(255) null,
    deadline        date         null,
    end             date         null,
    refunded        bit          not null,
    start           date         null,
    book_id         bigint       not null,
    customer_id     bigint       not null,
    constraint checkout_book_customer_unique_key
        unique (customer_id, book_id, checkout_number, refunded),
    constraint checkout_number_unique_key
        unique (checkout_number),
    constraint checkout_book_fk_id
        foreign key (book_id) references library.book (id),
    constraint checkout_customer_fk_id
        foreign key (customer_id) references library.customer (id)
);

create table library.participator
(
    id                 bigint auto_increment
        primary key,
    name               varchar(255)                  null,
    participation_type enum ('TRANSLATOR', 'WRITER') null
);

create table library.book_translators
(
    book_id       bigint not null,
    translator_id bigint not null,
    primary key (book_id, translator_id),
    constraint book_translators_book_fk_id
        foreign key (book_id) references library.book (id),
    constraint book_translators_translator_fk_id
        foreign key (translator_id) references library.participator (id)
);

create table library.book_writers
(
    book_id   bigint not null,
    writer_id bigint not null,
    primary key (book_id, writer_id),
    constraint book_writers_book_fk_id
        foreign key (book_id) references library.book (id),
    constraint book_writers_writer_fk_id
        foreign key (writer_id) references library.participator (id)
);

