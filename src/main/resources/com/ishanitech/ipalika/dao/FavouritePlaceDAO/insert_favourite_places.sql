INSERT INTO favourite_place (
	`fav_place_id`,
	`fav_place_name`,
	`fav_place_desc`,
	`fav_place_photo`,
	`fav_place_location`,
	`fav_place_ward`,
	`fav_place_type`,
	`added_by`
) VALUE
(
	:favPlaceId,
	:favPlaceName,
	:favPlaceDesc,
	:favPlacePhoto,
	:favPlaceLocation,
	:favPlaceWard,
	:favPlaceType,
	:addedBy
);