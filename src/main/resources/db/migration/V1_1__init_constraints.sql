alter table blog_review
    add constraint fk_blogreview_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table external_review
    add constraint fk_externalreview_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table group_restaurant
    add constraint fk_grouprestaurant_to_group
        foreign key (group_id)
            references saved (group_id);

alter table group_restaurant
    add constraint fk_grouprestaurant_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table member
    add constraint fk_member_to_oauth
        foreign key (oauth_id)
            references oauth (oauth_id);

alter table menu
    add constraint fk_menu_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table menu_pictures
    add constraint fk_menupicture_to_menu
        foreign key (menu_menu_id)
            references menu (menu_id);

alter table restaurant_picture
    add constraint fk_restaurantpicture_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table review
    add constraint fk_review_to_member
        foreign key (member_id)
            references member (member_id);

alter table review
    add constraint fk_review_to_restaurant
        foreign key (restaurant_id)
            references restaurant (restaurant_id);

alter table saved
    add constraint fk_saved_to_member
        foreign key (member_id)
            references member (member_id);