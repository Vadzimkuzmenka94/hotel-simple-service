create table public.customer (
                                 customer_id  bigserial not null,
                                 card_number varchar(255),
                                 email varchar(255),
                                 login varchar(255),
                                 name varchar(255),
                                 password varchar(255),
                                 role varchar(255),
                                 surname varchar(255),
                                 activation_code varchar(255),
                                 primary key (customer_id)
);


create table public.rooms (
                              id  bigserial not null,
                              breakfast boolean,
                              comment varchar(255),
                              conditioner boolean,
                              cost float8,
                              free boolean,
                              free_parking boolean,
                              fridge boolean,
                              no_smoking_room boolean,
                              number_of_beds int4,
                              room_number int4,
                              wifi boolean,
                              primary key (id)
);



create table booking (
                         id  bigserial not null,
                         cost float8,
                         currency varchar(255),
                         date timestamp,
                         duration int4,
                         finish_booking timestamp,
                         name varchar(255),
                         room_number int4,
                         start_booking timestamp,
                         customer_id int8,
                         primary key (id)
);


alter table booking
    add constraint FKlnnelfsha11xmo2ndjq66fvro
        foreign key (customer_id)
            references public.customer
