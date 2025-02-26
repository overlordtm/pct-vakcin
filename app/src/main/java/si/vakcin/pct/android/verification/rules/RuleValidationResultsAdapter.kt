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
 *  Created by osarapulov on 6/18/21 8:57 AM
 */

package si.vakcin.pct.android.verification.rules

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import si.vakcin.pct.android.R
import si.vakcin.pct.android.databinding.ItemRuleValidationResultBinding
import dgca.verifier.app.engine.Result
import java.util.*

/*-
 * ---license-start
 * eu-digital-green-certificates / dgc-certlogic-android
 * ---
 * Copyright (C) 2021 T-Systems International GmbH and all other contributors
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---license-end
 *
 * Created by osarapulov on 18.06.21 8:57
 */
class RuleValidationResultsAdapter(
    private val inflater: LayoutInflater,
    ruleValidationResultCards: Collection<RuleValidationResultCard>
) :
    RecyclerView.Adapter<RuleValidationResultsAdapter.CardViewHolder>() {

    private val ruleValidationResultCards: MutableList<RuleValidationResultCard> =
        ruleValidationResultCards.toMutableList()

    class CardViewHolder(private val binding: ItemRuleValidationResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun Result.getLocalizedText(context: Context): String = context.getString(
            when (this) {
                Result.PASSED -> R.string.passed
                Result.FAIL -> R.string.failed
                Result.OPEN -> R.string.open
            }
        )

        fun bind(ruleValidationResultCard: RuleValidationResultCard) {
            binding.ruleVerificationResultHeader.text =
                ruleValidationResultCard.result.getLocalizedText(
                    itemView.context
                )

            binding.ruleVerificationResultHeader.setTextColor(
                ResourcesCompat.getColor(
                    itemView.resources,
                    when (ruleValidationResultCard.result) {
                        Result.PASSED -> R.color.green
                        Result.OPEN -> R.color.grey_50
                        Result.FAIL -> R.color.red
                    },
                    null
                )
            )
            binding.description.text = ruleValidationResultCard.description
            binding.result.text = itemView.resources.getString(
                when (ruleValidationResultCard.result) {
                    Result.PASSED -> R.string.passed_for
                    Result.OPEN -> R.string.open_for
                    Result.FAIL -> R.string.failed_for
                },
                Locale("", ruleValidationResultCard.countryIsoCode).displayCountry
            )
            if (ruleValidationResultCard.current.isNotBlank()) {
                binding.current.text = ruleValidationResultCard.current
                View.VISIBLE
            } else {
                View.GONE
            }.apply {
                binding.ruleVerificationCurrentTitle.visibility = this
                binding.current.visibility = this
            }
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup) =
                CardViewHolder(ItemRuleValidationResultBinding.inflate(inflater, parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder.create(inflater, parent)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(ruleValidationResultCards[position])
    }

    override fun getItemCount(): Int = ruleValidationResultCards.size
}
