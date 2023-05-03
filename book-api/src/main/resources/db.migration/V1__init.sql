drop table if exists "books";

create table "books"
(
    "isbn"      text not null,
    "title"     text,
    "author_id" bigint,
    constraint "books_pkey" primary key ("isbn")
);