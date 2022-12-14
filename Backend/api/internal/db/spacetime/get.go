package spacetime

import (
	"time"

	"github.com/Hackathon-for-FUN-TeamA/backend/internal/db"
)

func GetByTime(time time.Time) ([]SpaceTime, error) {
	// dbmap初期化
	dbmap, err := db.InitDb()
	if err != nil {
		return []SpaceTime{}, err
	}
	defer dbmap.Db.Close()

	// time指定でspacetimesのデータを取得
	var spacetimes []SpaceTime
	_, err = dbmap.Select(&spacetimes, "SELECT * FROM spacetime WHERE time=?", time)
	if err != nil {
		return []SpaceTime{}, err
	}

	return spacetimes, nil
}
