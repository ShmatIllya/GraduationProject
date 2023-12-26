create table chats
(
    chats_id    int auto_increment
        primary key,
    description varchar(100) null,
    name        varchar(100) null,
    imageName   varchar(300) null
);

create table items
(
    item_id     int auto_increment
        primary key,
    name        varchar(100) null,
    articul     varchar(100) null,
    price       int          null,
    taxes       int          null,
    measurement varchar(100) null
);

create table personal
(
    personal_id int auto_increment
        primary key,
    login       varchar(100)  null,
    password    varchar(100)  null,
    nameSername varchar(200)  null,
    contacts    varchar(100)  null,
    email       varchar(100)  null,
    role        varchar(100)  null,
    subrole     varchar(100)  null,
    status      varchar(100)  null,
    description varchar(1000) null,
    regDate     date          null,
    imageName   varchar(300)  null
);

create table chat_members
(
    chat_members_id int auto_increment
        primary key,
    personal_id     int null,
    chat_id         int null,
    constraint fk_chat_members
        foreign key (chat_id) references chats (chats_id)
            on delete cascade,
    constraint fk_members_chat
        foreign key (personal_id) references personal (personal_id)
            on delete cascade
);

create table clients
(
    clients_id     int auto_increment
        primary key,
    name           varchar(100) null,
    responsable_id int          null,
    phone          varchar(100) null,
    email          varchar(100) null,
    adress         varchar(100) null,
    description    varchar(400) null,
    type           varchar(100) null,
    work_type      varchar(100) null,
    reg_date       date         null,
    constraint fk_personal_clients
        foreign key (responsable_id) references personal (personal_id)
);

create table desks
(
    desk_id     int auto_increment
        primary key,
    name        varchar(50) charset utf8mb3 null,
    personal_id int                         null,
    constraint fk_desks_personal
        foreign key (personal_id) references personal (personal_id)
            on delete cascade
);

create table messages
(
    messages_id int auto_increment
        primary key,
    text        varchar(200) null,
    date        date         null,
    sender_id   int          null,
    chat_id     int          null,
    time        time         null,
    constraint fk_chat_message
        foreign key (chat_id) references chats (chats_id)
            on delete cascade,
    constraint fk_sender_message
        foreign key (sender_id) references personal (personal_id)
            on delete cascade
);

create table message_status
(
    id          int auto_increment
        primary key,
    personal_id int                         null,
    message_id  int                         null,
    status      varchar(50) charset utf8mb3 null,
    chat_id     int                         null,
    constraint fk_chat_id
        foreign key (chat_id) references chats (chats_id)
            on delete cascade,
    constraint fk_messages
        foreign key (message_id) references messages (messages_id)
            on delete cascade,
    constraint fk_personal
        foreign key (personal_id) references personal (personal_id)
            on delete cascade
);

create table payments
(
    payment_id         int auto_increment
        primary key,
    creation_date      date                         null,
    deadline           date                         null,
    sub_info           varchar(100)                 null,
    paymenter_id       int                          null,
    reciever_id        int                          null,
    item_id            int                          null,
    amount             int                          null,
    final_price        int                          null,
    status             varchar(100)                 null,
    payment_image_name varchar(100) charset utf8mb3 null,
    constraint fk_client_paymenter_payments
        foreign key (paymenter_id) references clients (clients_id)
            on delete cascade,
    constraint fk_client_reciever_payments
        foreign key (reciever_id) references clients (clients_id)
            on delete cascade,
    constraint fk_items_payments
        foreign key (item_id) references items (item_id)
            on delete cascade
);

create table projects
(
    projects_id    int auto_increment
        primary key,
    name           varchar(100)                 null,
    description    varchar(200)                 null,
    responsable_id int                          null,
    deadline       date                         null,
    status         varchar(100)                 null,
    creation_date  date                         null,
    trudozatraty   varchar(50) charset utf8mb3  null,
    start_control  varchar(100) charset utf8mb3 null,
    end_control    varchar(100) charset utf8mb3 null,
    checker_id     int                          null,
    plan_control   varchar(50) charset utf8mb3  null,
    izm            varchar(30) charset utf8mb3  null,
    constraint fk_checker_project
        foreign key (checker_id) references personal (personal_id)
            on delete cascade,
    constraint fk_responsable_projects
        foreign key (responsable_id) references personal (personal_id)
            on delete cascade
);

create table processes
(
    processes_id   int auto_increment
        primary key,
    status         varchar(100) null,
    client_id      int          null,
    responsable_id int          null,
    checker_id     int          null,
    payment        varchar(100) null,
    description    varchar(100) null,
    project_id     int          null,
    constraint fk_checker_process
        foreign key (checker_id) references personal (personal_id)
            on delete cascade,
    constraint fk_client_process
        foreign key (client_id) references clients (clients_id)
            on delete cascade,
    constraint fk_project_process
        foreign key (project_id) references projects (projects_id)
            on delete cascade,
    constraint fk_responsable_process
        foreign key (responsable_id) references personal (personal_id)
            on delete cascade
);

create table process_members
(
    process_members_id int auto_increment
        primary key,
    process_id         int null,
    personal_id        int null,
    constraint fk_members_process
        foreign key (personal_id) references personal (personal_id)
            on delete cascade,
    constraint fk_process_members
        foreign key (process_id) references processes (processes_id)
            on delete cascade
);

create table project_members
(
    project_members_id int auto_increment
        primary key,
    project_id         int                          null,
    personal_id        int                          null,
    team_name          varchar(100) charset utf8mb3 null,
    constraint fk_members_project
        foreign key (personal_id) references personal (personal_id)
            on delete cascade,
    constraint fk_project_members
        foreign key (project_id) references projects (projects_id)
            on delete cascade
);

create table tasks
(
    tasks_id       int auto_increment
        primary key,
    name           varchar(100)                null,
    responsable_id int                         null,
    description    varchar(300)                null,
    checker_id     int                         null,
    deadline       date                        null,
    project_id     int                         null,
    process_id     int                         null,
    status         varchar(100)                null,
    client_id      int                         null,
    creation_date  datetime                    null,
    priority       varchar(50) charset utf8mb3 null,
    constraint fk_checker_tasks
        foreign key (checker_id) references personal (personal_id)
            on delete cascade,
    constraint fk_client_tasks
        foreign key (client_id) references clients (clients_id)
            on delete cascade,
    constraint fk_process_tasks
        foreign key (process_id) references processes (processes_id)
            on delete cascade,
    constraint fk_project_tasks
        foreign key (project_id) references projects (projects_id)
            on delete cascade,
    constraint fk_responsable_tasks
        foreign key (responsable_id) references personal (personal_id)
            on delete cascade
);

create table business
(
    business_id    int auto_increment
        primary key,
    name           varchar(100) null,
    description    varchar(100) null,
    client_id      int          null,
    date           datetime     null,
    place          varchar(100) null,
    responsable_id int          null,
    process_id     int          null,
    status         varchar(100) null,
    task_id        int          null,
    project_id     int          null,
    constraint fk_client_business
        foreign key (client_id) references clients (clients_id)
            on delete cascade,
    constraint fk_process_business
        foreign key (process_id) references processes (processes_id)
            on delete cascade,
    constraint fk_project_business
        foreign key (project_id) references projects (projects_id)
            on delete cascade,
    constraint fk_responsable_business
        foreign key (responsable_id) references personal (personal_id)
            on delete cascade,
    constraint fk_tasks_business
        foreign key (task_id) references tasks (tasks_id)
            on delete cascade
);

create table journals
(
    journals_id int auto_increment
        primary key,
    process_id  int null,
    task_id     int null,
    project_id  int null,
    client_id   int null,
    constraint fk_client_journal
        foreign key (client_id) references clients (clients_id)
            on delete cascade,
    constraint fk_process_journal
        foreign key (process_id) references processes (processes_id)
            on delete cascade,
    constraint fk_project_journal
        foreign key (project_id) references projects (projects_id)
            on delete cascade,
    constraint fk_task_journal
        foreign key (task_id) references tasks (tasks_id)
            on delete cascade
);

create table comments
(
    comments_id int auto_increment
        primary key,
    text        varchar(300) null,
    date        datetime     null,
    sender_id   int          null,
    journal_id  int          null,
    constraint fk_journal_comment
        foreign key (journal_id) references journals (journals_id)
            on delete cascade,
    constraint fk_sender_comment
        foreign key (sender_id) references personal (personal_id)
            on delete cascade
);


