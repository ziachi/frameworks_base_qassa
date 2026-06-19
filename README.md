# frameworks_base_qassa

Custom `frameworks/base` for **Qassa ROM** (Android 10) — Xiaomi Redmi 4X (santoni).

## Overview

This repo tracks local patches on top of [keepQASSA/frameworks_base](https://github.com/keepQASSA/frameworks_base) (`Q` branch). Only custom commits are pushed here; the full AOSP/Qassa base is fetched via `repo sync`.

## Branch

| Branch | Base | Description |
|--------|------|-------------|
| `qassa-10` | `keepQASSA/frameworks_base` @ `Q` | Custom patches for santoni |

## Current Patches

| Commit | Description |
|--------|-------------|
| `32194256` | Update 4G/4G+ icon |
| `dd26ea41` | ~~Integrate Android Override PropsHooks~~ (removed) |
| `3703a3fa` | Remove Android Override framework hooks |

> **Note:** Android Override integration was added and later removed. The framework is now clean — no spoofing hooks, no attestation interception.

## Related Repos

| Repo | Branch | Purpose |
|------|--------|---------|
| [device_xiaomi_santoni_qassa](https://github.com/ziachi/device_xiaomi_santoni_qassa) | `qassa-dev` | Device tree |
| [vendor_xiaomi_santoni_qassa](https://github.com/ziachi/vendor_xiaomi_santoni_qassa) | `10.0` | Vendor blobs |
| [kernel_xiaomi_msm8937_qassa](https://github.com/ziachi/kernel_xiaomi_msm8937_qassa) | `13` | Kernel |

## Build

```bash
# In Qassa ROM source tree
cd frameworks/base
git remote add ziachi https://github.com/ziachi/frameworks_base_qassa.git
git fetch ziachi
git checkout ziachi/qassa-10

# Build
cd ~/qassa
. build/envsetup.sh
lunch qassa_santoni-userdebug
mka qassa -j$(nproc)
```

## Maintainer

- **ziachi** — [@ziachi](https://github.com/ziachi)
