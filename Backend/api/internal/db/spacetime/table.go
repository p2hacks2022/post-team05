package spacetime

type SpaceTime struct {
	Id         int     `db:"id"`
	Time       string  `db:"time"`
	Latitude   float64 `db:"latitude"`
	Longtitude float64 `db:"longtitude"`
	Altitude   float64 `db:"altitude"`
	ObjId      int     `db:"obj_id"`
}
