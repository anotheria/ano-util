# ano-util Change Log

### Unreleased
* Dependency updates: parent 4.6, jackson-bom 2.22.4, configureme 4.1.1, bcprov-jdk18on 1.86. See https://github.com/anotheria/ano-util/pull/18, https://github.com/anotheria/ano-util/pull/19 and https://github.com/anotheria/ano-util/pull/20

### 4.0.10 2026-10-25
* Fix: `TemplateUtility` failed with `NoClassDefFoundError` when jackson-databind was not on the classpath (regression of 4.0.9, where jackson became optional). `LoopTemplateProcessor` now loads jackson only when a loop element is actually mapped. See https://github.com/anotheria/ano-util/pull/21

### 4.0.9 2026-10-05
* **Important:** jackson-databind is now an optional dependency and is no longer pulled in transitively. It is only needed by `LoopTemplateProcessor`; projects using `{loop:...}` templates must declare jackson-databind themselves. (Note: use 4.0.10, 4.0.9 fails without jackson on the classpath.)
* Jackson is managed through jackson-bom (2.22.3), so databind, core and annotations share one version.
* Fix: `LoopTemplateProcessor` placeholder substitution:
  * property names are substituted longest first, so a short name no longer consumes the prefix of a longer one (e.g. `itemsData.name` vs. `itemsData.nameId`);
  * a collection of scalars no longer aborts the whole template with an `IllegalArgumentException`, such elements render as empty values;
  * properties with null values no longer leak their literal placeholder text into the output;
  * null collection elements are handled.
* CI: added a Build workflow running the test suite on push and pull request; Dependabot groups jackson and patch-level updates.

### 4.0.8 2026-09-18
* Fix: `StringUtils._tokenize` dropped the trailing empty token when `skipEmptyTokens` was off. Visible in `CSVParser`: a row whose last field was empty came back one cell short of the header. See https://github.com/anotheria/ano-util/pull/14

### 4.0.7 2026-07-19
* Dependency updates: parent 4.5, configureme 4.1.0 (dependencies cleanup), jackson-databind 2.22.1.

### 4.0.6 2026-07-10
- Dependency update for jackson-databind

### 4.0.5 2026-06-03
 - Fix: CryptTool now accepts Blowfish keys longer than 56 bytes (448 bits). BouncyCastle post-1.68 introduced a strict key-length guard that rejected keys previously accepted by BC 1.68, breaking decryption of existing data encrypted with long keys. Fixed by bundling LegacyBlowfishEngine (BC 1.80 source, MIT licence) with the guard removed, preserving the original key-cycling behaviour.

### 4.0.4 2026-05-30
 - Security: Replaced deprecated bcprov-jdk15on:1.68 with bcprov-jdk18on:1.84, resolving 5 CVEs (LDAP injection, Marvin attack, DoS, DNS poisoning, certificate
  parsing). Byte-level cipher output is unchanged — existing encrypted data remains compatible.
  - CI: Upgraded CodeQL action from v2 to v3; added develop branch to CodeQL workflow triggers.



### 4.0.3 2025-10-14
* Downgraded version of bouncycastle to 1.68 because 1.70 was creating incompatibilities with existing encrypted tokens. 

### 4.0.2 2025-10-01
* Added [Loop-Processor](https://github.com/anotheria/ano-util/wiki/HowTO:-Loop-Processor) to TemplateProcessor for processing of multiple elements in an email or similar. 


### 4.0.1 2025-09-24
* Migrated ByteArraySerializer from ano-net. This is done to sunset ano-net and to reduce dependencies in projects that use ano-util and ano-net (now they don't have to have ano-net dependency anymore).

### 4.0.0
* Java11++ compatible version

### 2.2.2 2023-11-01
* Minor improvements in CSV Output in DataCell/DataTable