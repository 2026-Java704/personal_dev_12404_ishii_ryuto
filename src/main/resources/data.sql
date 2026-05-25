INSERT INTO users ( name, password)
 VALUES ( '鈴木一郎', 'himitu'), ('佐藤悠介', 'okusuri'), ( '田中愛子', 'check');

 INSERT INTO medicine (name, note, count,m_check,date,dtime, users_id)
  VALUES
  ('風邪薬', '食後1回2錠', 2, FALSE,'2026-05-13','朝', 1),
  ('頭痛薬', '食後1回1錠', 1, FALSE,'2026-05-20' ,'昼',1),
  ('イブプロフェン', '食後1回1錠', 1, FALSE,'2026-05-19','晩', 2),
  ('ビオフェルミン', '食後1回3錠', 3, FALSE,'2026-05-01' ,'朝',3),
  ('ロキソニン', '食後1回1錠', 2,FALSE,'2026-05-07' ,'晩',1);