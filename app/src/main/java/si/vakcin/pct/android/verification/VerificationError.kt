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
 *  Created by osarapulov on 5/24/21 11:39 PM
 */

package si.vakcin.pct.android.verification

import dgca.verifier.app.decoder.model.VerificationResult

enum class VerificationError {
    GREEN_CERTIFICATE_EXPIRED,
    CERTIFICATE_EXPIRED,
    CERTIFICATE_REVOKED,
    VERIFICATION_FAILED,
    TEST_DATE_IS_IN_THE_FUTURE,
    TEST_RESULT_POSITIVE,
    RECOVERY_NOT_VALID_SO_FAR,
    RECOVERY_NOT_VALID_ANYMORE,
    RULES_VALIDATION_FAILED,
    CRYPTOGRAPHIC_SIGNATURE_INVALID
}

fun VerificationResult.fetchError(innerVerificationResult: InnerVerificationResult): VerificationError? =
    when {
        isValid() && innerVerificationResult.isValid() -> null
        innerVerificationResult.noPublicKeysFound-> VerificationError.VERIFICATION_FAILED
        innerVerificationResult.certificateExpired -> VerificationError.CERTIFICATE_EXPIRED
        !coseVerified -> VerificationError.CRYPTOGRAPHIC_SIGNATURE_INVALID
        !isNotExpired -> VerificationError.GREEN_CERTIFICATE_EXPIRED
        isTestDateInTheFuture() -> VerificationError.TEST_DATE_IS_IN_THE_FUTURE
        isTestWithPositiveResult() -> VerificationError.TEST_RESULT_POSITIVE
        isRecoveryNotValidSoFar() -> VerificationError.RECOVERY_NOT_VALID_SO_FAR
        isRecoveryNotValidAnymore() -> VerificationError.RECOVERY_NOT_VALID_ANYMORE
        rulesValidationFailed -> VerificationError.RULES_VALIDATION_FAILED
        else -> VerificationError.CRYPTOGRAPHIC_SIGNATURE_INVALID
    }
