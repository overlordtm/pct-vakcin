/*
 *  ---license-start
 *  eu-digital-green-certificates / dgca-verifier-app-android
 *  ---
 *  Copyright (C) 2021 T-Systems International GmbH and all other contributors
 *  ---
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ---license-end
 *
 *  Created by osarapulov on 7/26/21 12:08 PM
 */

package si.vakcin.pct.android.data.local.valuesets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.databind.JsonNode
import java.time.LocalDate

@Entity(tableName = "valuesets")
class ValueSetLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val valueSetId: String,
    val valueSetDate: LocalDate,
    val valueSetValues: JsonNode
)