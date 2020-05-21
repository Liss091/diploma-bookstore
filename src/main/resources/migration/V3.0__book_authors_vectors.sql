alter table books
add column title_vector tsvector;

update books
set title_vector = to_tsvector(title);

create index title_index
on books
using gin(title_vector);

alter table authors
add column name_vector tsvector;

update authors
set name_vector =
to_tsvector(coalesce(first_name, '') || ' ' || coalesce(middle_name, '') || ' ' || coalesce(last_name, ''));

create index name_index
on authors
using gin(name_vector);