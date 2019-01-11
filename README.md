# Nimble-Android-Assignment


App Usage Notes:
- Please drag viewPager vertically to swipe across the pages
- When you are on the last page of ViewPager and you drag from bottom to top, next page gets loaded view api
- When you are on first page of ViewPager and drag from top to bottom, data gets reloaded from page 1.

Other used components:
- MVVM (Android architecture components)
- Kotlin
- DataBinding
- ViewHolderPattern
- Authenticator to refresh the expired token (TokenAuthenticator)
- Vertical ViewPager with pagination (page size 5)
- Dagger(v-2.9) is used, but not to its full strength
- Retrofit
- Constraint Layout


Things that should have added:
- FastLane/Bitrise
- Crashlytics
- Fabric
- SonarCube
- Proguard
- Product Flavours

Notes:
- Could not move to AndroidX just for verticalViewPager library, its old and could not get migrated to AndroidX
- Test coverage is not added (Expresso for UI, jUnit for unitTests)
