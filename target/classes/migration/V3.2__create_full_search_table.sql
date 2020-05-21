create table full_search_vector (
	book_id integer CONSTRAINT PK_Search PRIMARY KEY,
	full_tsvector tsvector
);


insert into full_search_vector
select b.book_id,
setweight(to_tsvector(b.title), 'A')
|| ' ' ||
setweight(to_tsvector(string_agg(coalesce(au.first_name, '') || ' ' ||
							coalesce(au.middle_name, '') || ' ' ||
							coalesce(au.last_name, ''), ' ')), 'B')
|| ' ' ||
setweight(to_tsvector((select string_agg( coalesce(g.genre, ''), ' ')
							    from books b2
							    left join book_genres bg on bg.book_id=b2.book_id
								left join genres g on bg.genre_id=g.genre_id
							    where b.book_id=b2.book_id
							   	group by b2.book_id)), 'D')
from books b
left join book_authors ba on b.book_id=ba.book_id
left join authors au on ba.author_id=au.author_id
group by b.book_id;


create function full_search_update_trigger() returns trigger as $$
begin
insert into full_search_vector
	select b.book_id,
		setweight(to_tsvector(b.title), 'A')
		|| ' ' ||
		setweight(to_tsvector(string_agg(coalesce(au.first_name, '') || ' ' ||
							coalesce(au.middle_name, '') || ' ' ||
							coalesce(au.last_name, ''), ' ')), 'B')
		|| ' ' ||
		setweight(to_tsvector((select string_agg( coalesce(g.genre, ''), ' ')
							    from books b2
							    left join book_genres bg on bg.book_id=b2.book_id
								left join genres g on bg.genre_id=g.genre_id
							    where b.book_id=b2.book_id
							   	group by b2.book_id)), 'D')
	from books b
	left join book_authors ba on b.book_id=ba.book_id
	left join authors au on ba.author_id=au.author_id
	where b.book_id=new.book_id
	group by b.book_id
on conflict ON CONSTRAINT PK_Search
do
	update
	set full_tsvector = (select setweight(to_tsvector(b.title), 'A')
		|| ' ' ||
		setweight(to_tsvector(string_agg(coalesce(au.first_name, '') || ' ' ||
							coalesce(au.middle_name, '') || ' ' ||
							coalesce(au.last_name, ''), ' ')), 'B')
		|| ' ' ||
		setweight(to_tsvector((select string_agg( coalesce(g.genre, ''), ' ')
							    from books b2
							    left join book_genres bg on bg.book_id=b2.book_id
								left join genres g on bg.genre_id=g.genre_id
							    where b.book_id=b2.book_id
							   	group by b2.book_id)), 'D')
	from books b
	left join book_authors ba on b.book_id=ba.book_id
	left join authors au on ba.author_id=au.author_id
	where b.book_id = new.book_id
	group by b.book_id)
where full_search_vector.book_id=new.book_id;
return new;
end
$$ language plpgsql;

create trigger full_search_vector_book_update before insert or update
on books for each row execute procedure full_search_update_trigger();