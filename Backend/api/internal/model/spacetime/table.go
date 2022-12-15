package spacetime

type SpaceTime struct {
	Id         int     `db:"id"`
	Time       string  `db:"time"`
	Latitude   float32 `db:"latitude"`
	Longtitude float32 `db:"longtitude"`
	Altitude   float32 `db:"altitude"`
	ObjId      int     `db:"obj_id"`
}
