CREATE OR REPLACE function book_title_tsvector_trigger() returns trigger as $$
begin
new.title_vector := to_tsvector(new.title);
return new;
end
$$ language plpgsql;

CREATE trigger book_title_tsvector_update before insert or update
on books for each row execute procedure book_title_tsvector_trigger();

create function author_name_tsvector_trigger() returns trigger as $$
begin
new.name_vector := to_tsvector(coalesce(new.first_name, '') || ' ' || coalesce(new.middle_name, '') || ' ' || coalesce(new.last_name, ''));
return new;
end
$$ language plpgsql;

create trigger author_name_tsvector_update before insert or update
on authors for each row execute procedure author_name_tsvector_trigger();