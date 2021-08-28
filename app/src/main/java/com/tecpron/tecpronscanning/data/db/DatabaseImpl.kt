package com.tecpron.tecpronscanning.data.db

import javax.inject.Inject

class DatabaseImpl @Inject constructor(
    override val stationDao: StationDao
) : Database