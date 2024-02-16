SELECT
et.form_id as formId,
et.token_id as tokenId,
et.answer_1 as answer1,
et.answer_2 as answer2,
et.answer_3 as answer3,
et.answer_4 as answer4,
et.answer_5 as answer5,
et.answer_6 as answer6,
et.answer_7 as answer7,
et.answer_8 as answer8,
et.answer_9 as answer9,
et.answer_10 as answer10,
et.answer_11 as answer11,
et.answer_12 as answer12,
et.answer_13 as answer13,
et.answer_14 as answer14,
et.answer_15 as answer15,
et.answer_16 as answer16,
et.answer_17 as answer17,
et.answer_18 as answer18,
et.answer_19 as answer19,
et.answer_20 as answer20,
et.answer_21 as answer21,
et.answer_22 as answer22,
et.answer_23 as answer23,
et.answer_24 as answer24,
et.answer_25 as answer25,
et.answer_26 as answer26,
et.answer_27 as answer27,
et.answer_28 as answer28,
et.answer_29 as answer29,
et.answer_30 as answer30,
lg.province_name as answer101,
ld.district_name as answer102,
lm.municipality_name as answer103,
lw.ward_description as answer104,
et.answer_105 as answer105
FROM <databaseTableName> et
INNER JOIN  lg_province lg on et.answer_101 = lg.province_id
INNER JOIN lg_districts ld on et.answer_102 = ld.district_id
INNER JOIN lg_municipality lm on et.answer_103 = lm.municipality_id
Inner JOIN lg_ward lw on et.answer_104 = lw.ward_id
WHERE et.token_id = :tokenId;