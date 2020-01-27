INSERT INTO user_profile(id, uuid, email, firstName, lastName) VALUES (1, '1c3153cc3f7611ea95a02e728ce88125', 'edson.nascimento@example.com', 'Edson', 'Nascimento');
INSERT INTO user_profile(id, uuid, email, firstName, lastName) VALUES (2, 'f84726e5ada74312ba2ade907d3faa5e', 'arthur.antunes@example.com', 'Arthur', 'Antunes');
INSERT INTO user_profile(id, uuid, email, firstName, lastName) VALUES (3, '86f7513bf83c4072868c1b4efc9d3425', 'ronaldo.nazario@example.com', 'Ronaldo', 'Nazario');

INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (1, '7daba3ef7a0642afac57a99087035191', 1, 'Safety', 'EUR');
INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (2, '2d9c04b53b0f478187f718ef9dd5b83f', 1, 'Main', 'EUR');
INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (3, '7daba3ef7a0642afac57a99087035192', 2, 'Safety', 'EUR');
INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (4, '2d9c04b53b0f478187f718ef9dd5b84f', 2, 'Main', 'EUR');
INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (5, '7daba3ef7a0642afac57a99087035193', 2, 'Travel', 'EUR');
INSERT INTO account(id, uuid, user_profile_id, nickname, currency) VALUES (6, '2d9c04b53b0f478187f718ef9dd5b85f', 3, 'Main', 'EUR');

INSERT INTO account_balance(id, account_id, amount) VALUES (1, 1, 500);
INSERT INTO account_balance(id, account_id, amount) VALUES (2, 2, 500);
INSERT INTO account_balance(id, account_id, amount) VALUES (3, 3, 500);
INSERT INTO account_balance(id, account_id, amount) VALUES (4, 4, 500);
INSERT INTO account_balance(id, account_id, amount) VALUES (5, 5, 500);
INSERT INTO account_balance(id, account_id, amount) VALUES (6, 6, 0);

INSERT INTO accounting_transaction(id, uuid, createdDate, reference, description) VALUES (1, '85a536d62fd34ce391c65f3f14f46afa', 1580069840697, 'doctor', 'tooth implant');

INSERT INTO accounting_entry(id, uuid, fromAccountReference, transaction_id, operation, currency, amount, account_id) VALUES (1, '9251cec230564d22aba14cb23bed9ef9', 'BR1234', 1, 'DEPOSIT', 'EUR', 100, 6);