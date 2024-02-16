SELECT tb.form_id, sl.status, sl.status_2_by, sl.status_2_in, tb.token_id as tokenId,
sl.status_3_by, sl.status_3_in, sl.status_4_by, sl.status_4_in, status_5_by, status_5_in ,
tb.answer_1, tb.answer_2, tb.answer_3, tb.answer_4, tb.answer_5, tb.answer_6,
tb.answer_7, tb.answer_8, tb.answer_9, tb.answer_10, tb.answer_11, tb.answer_12, tb.answer_13, tb.answer_14,
tb.answer_15, tb.answer_16, tb.answer_17, tb.answer_18, tb.answer_19, tb.answer_20, tb.answer_21, tb.answer_22,
tb.answer_23, tb.answer_24, tb.answer_25, tb.answer_26, tb.answer_27, tb.answer_28, tb.answer_29, tb.answer_30
 FROM <databaseTableName> AS tb
INNER JOIN standard_log sl ON tb.form_id = sl.form_id where tb.token_id = sl.token_id
