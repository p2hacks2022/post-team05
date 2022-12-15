package spacetime

import (
	"github.com/p2hacks2022/post-team05/internal/db"
)

// spacetimesにinsertする
func Post(time string, latitude, longtitude, altitude float32, objId int) error {
	// dbmap初期化
	dbmap, err := db.InitDb()
	if err != nil {
		return err
	}
	defer dbmap.Db.Close()

	// dbmapにテーブル名登録
	dbmap.AddTableWithName(SpaceTime{}, "spacetimes")

	// Insert
	spaceTimes := &SpaceTime{
		Time:       time,
		Latitude:   latitude,
		Longtitude: longtitude,
		Altitude:   altitude,
		ObjId:      objId,
	}
	err = dbmap.Insert(spaceTimes)
	if err != nil {
		return err
	}

	return nil
}
